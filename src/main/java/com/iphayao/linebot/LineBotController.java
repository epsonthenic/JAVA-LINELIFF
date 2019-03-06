package com.iphayao.linebot;

import com.google.common.io.ByteStreams;
import com.iphayao.linebot.flex.*;
import com.iphayao.linebot.helper.RichMenuHelper;
import com.iphayao.linebot.service.AppMailServiceImp;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.BeaconEvent;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.unit.FlexGravity;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Slf4j
@LineMessageHandler
public class LineBotController {
    @Autowired
    private LineMessagingClient lineMessagingClient;


    /////////////////////////////รับข้อความ////////////////////////////////
    @EventMapping
    public void handleTextMessage(MessageEvent<TextMessageContent> event) throws IOException {
        log.info(event.toString());
        handleTextContent(event.getReplyToken(), event, event.getMessage());
    }

    /////////////////////////////Sticker////////////////////////////////
    @EventMapping
    public void handleStickerMessage(MessageEvent<StickerMessageContent> event) {
        log.info(event.toString());
        StickerMessageContent message = event.getMessage();

        String stickerPackageId = "11537";
        String stickerStickerId = "52002768";

        log.info("StickerPackageId : {}",message.getPackageId());
        log.info("StickerStickerId : {}",message.getStickerId());

        reply(event.getReplyToken(), new StickerMessage(
                stickerPackageId,stickerStickerId

        ));
    }

    /////////////////////////////Location////////////////////////////////
    @EventMapping
    public void handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        log.info(event.toString());
        LocationMessageContent message = event.getMessage();
        log.info("Title : {}",message.getTitle());
        log.info("Address : {}",message.getAddress());
        log.info("Latitude : {}",message.getLatitude());
        log.info("Longitude : {}",message.getLongitude());

        reply(event.getReplyToken(), new LocationMessage(
                (message.getTitle() == null) ? "Location replied" : message.getTitle(),
                message.getAddress(),
                message.getLatitude(),
                message.getLongitude()
        ));
    }

    /////////////////////////////รูปภาพ////////////////////////////////
    @EventMapping
    public void handleImageMessage(MessageEvent<ImageMessageContent> event) {
        log.info(event.toString());
        ImageMessageContent content = event.getMessage();
        String replyToken = event.getReplyToken();

        try {
            MessageContentResponse response = lineMessagingClient.getMessageContent(content.getId()).get();
            DownloadedContent jpg = saveContent("jpg", response);

            reply(replyToken, new ImageMessage(jpg.getUri(),jpg.getUri()));

        } catch (InterruptedException | ExecutionException e) {
            reply(replyToken, new TextMessage("Cannot get image: " + content));
            throw new RuntimeException(e);
        }

    }

    /////////////////////////////LINE Beacon///////////////////////////
    @EventMapping
    public void handleBeaconEvent(BeaconEvent event) throws IOException {

        String replyToken = event.getReplyToken();
        String userId = event.getSource().getUserId();

        String pathImageFlex = new ClassPathResource("richmenu/OK (1).jpg").getFile().getAbsolutePath();
        String pathConfigFlex = new ClassPathResource("richmenu/richmenu-flexs.yml").getFile().getAbsolutePath();
        RichMenuHelper.createRichMenu(lineMessagingClient, pathConfigFlex, pathImageFlex, userId);
        this.reply(replyToken, new NewsFlexMessageSupplier().get());
    }






    private void handleTextContent(String replyToken, Event event, TextMessageContent content) throws IOException {
        String text = content.getText();
        String userId = event.getSource().getUserId();

        switch (text) {
            case "Flex": {
                String pathImageFlex = new ClassPathResource("richmenu/OK (1).jpg").getFile().getAbsolutePath();
                String pathConfigFlex = new ClassPathResource("richmenu/richmenu-flexs.yml").getFile().getAbsolutePath();
                RichMenuHelper.createRichMenu(lineMessagingClient, pathConfigFlex, pathImageFlex, userId);
                break;
            }
            case "ปิดเมนู": {
                RichMenuHelper.deleteRichMenu(lineMessagingClient, userId);
                break;
            }
            case "ร้านอาหาร": {
                this.reply(replyToken, new RestaurantFlexMessageSupplier().get());
                break;
            }
            case "เมนู": {
                this.reply(replyToken, new RestaurantMenuFlexMessageSupplier().get());
                break;
            }
            case "ใบเสร็จ": {
                this.reply(replyToken, new ReceiptFlexMessageSupplier().get());
                break;
            }
            case "ข่าว": {
                this.reply(replyToken, new NewsFlexMessageSupplier().get());
                break;
            }
            case "Ticket": {
                this.reply(replyToken, new TicketFlexMessageSupplier().get());
                break;
            }
            case "Catalogue": {
                this.reply(replyToken, new CatalogueFlexMessageSupplier().get());
                break;
            }
            default:
                String pathImageFlex = new ClassPathResource("richmenu/OK (1).jpg").getFile().getAbsolutePath();
                String pathConfigFlex = new ClassPathResource("richmenu/richmenu-flexs.yml").getFile().getAbsolutePath();
                RichMenuHelper.createRichMenu(lineMessagingClient, pathConfigFlex, pathImageFlex, userId);
                boolean hasText = text.contains("@");
                boolean hasText3 = text.contains("ขอเข้ากลุ่ม");
                if (hasText3 == true) {
                    reply(replyToken, Arrays.asList(
                            new TextMessage("https://line.me/R/ti/g/vYYHUCuMG_")
                    ));
                } else if ((AppMailServiceImp.checkTextMatches(text) == true) && (hasText == false)) {
                    if (userId != null) {
                        lineMessagingClient.getProfile(userId)
                                .whenComplete((profile, throwable) -> {
                                    if (throwable != null) {
                                        this.replyText(replyToken, throwable.getMessage());
                                        return;
                                    }
                                    this.reply(replyToken, Arrays.asList(
                                            new TextMessage("มี keyword ไม่มี @"),
                                            new TextMessage("ชื่อคุณคือ: " + profile.getDisplayName()),
                                            new TextMessage("ID คุณคือ: " + profile.getUserId())
                                    ));
                                });
                    }
                } else if ((hasText == true) && (AppMailServiceImp.checkTextMatches(text) == true)) {
                    if (userId != null) {
                        reply(replyToken, Arrays.asList(
                                new TextMessage("มี keyword และมี @"),
                                new TextMessage(text)
                        ));
                    }
                }
        }
    }


    private static DownloadedContent saveContent(String ext, MessageContentResponse response) {
        log.info("Content-type: {}", response);
        DownloadedContent tempFile = createTempFile(ext);
        try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
            ByteStreams.copy(response.getStream(), outputStream);
            log.info("Save {}: {}", ext, tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static DownloadedContent createTempFile(String ext) {
        String fileName = LocalDateTime.now() + "-" + UUID.randomUUID().toString() + "." + ext;
        Path tempFile = Application.downloadedContentDir.resolve(fileName);
        tempFile.toFile().deleteOnExit();
        return new DownloadedContent(tempFile, createUri("/downloaded/" + tempFile.getFileName()));

    }

    private static String createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path).toUriString();
    }

    @Value
    public static class DownloadedContent {
        Path path;
        String uri;
    }

    private void replyText(@NonNull  String replyToken, @NonNull String message) {
        if(replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken is not empty");
        }

        if(message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "...";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse response = lineMessagingClient.replyMessage(
                    new ReplyMessage(replyToken, messages)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
