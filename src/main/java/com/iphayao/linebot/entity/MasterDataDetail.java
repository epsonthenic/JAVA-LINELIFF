package com.iphayao.linebot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
public class MasterDataDetail {



	private @Id @GeneratedValue(strategy=GenerationType.TABLE) Long id;
	private @Version @JsonIgnore Long version;
	private String createdBy;
	private @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp createdDate;
	private String updateBy;
	private @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp updateDate;

	private String code;
	private String name;
	private String description;
	private Boolean flagActive;
	private String orgCode;



	public MasterDataDetail(String code, String name, String description) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.flagActive = true;
	}



	public MasterDataDetail(String code, String name, String description, Boolean flagActive, String variable1,
			String variable2) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.flagActive = flagActive;
		this.variable1 = variable1;
		this.variable2 = variable2;
	}


	public MasterDataDetail(String code, String name, String description,  String variable1,
							String variable2,String variable3, String variable4, String variable5, String variable6,String variable7) {
		super();
		this.flagActive = true;
		this.code = code;
		this.name = name;
		this.description = description;
		this.variable1 = variable1;
		this.variable2 = variable2;
		this.variable3 = variable3;
		this.variable4 = variable4;
		this.variable5 = variable5;
		this.variable6 = variable6;
		this.variable7 = variable7;
	}

	public MasterDataDetail(String code, String name, String description,  String variable1,
			String variable2,String variable3, String variable4, String variable5, String variable6,String variable7
			, String variable8, String variable9,String variable10) {
		super();
		this.flagActive = true;
		this.code = code;
		this.name = name;
		this.description = description;
		this.variable1 = variable1;
		this.variable2 = variable2;
		this.variable3 = variable3;
		this.variable4 = variable4;
		this.variable5 = variable5;
		this.variable6 = variable6;
		this.variable7 = variable7;
		this.variable8 = variable8;
		this.variable9 = variable9;
		this.variable10 = variable10;
	}



	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterdata")
    private MasterData masterdata;

	private String variable1;
	private String variable2;
	private String variable3;
	private String variable4;
	private String variable5;
	private String variable6;
	private String variable7;
	private String variable8;
	private String variable9;
	private String variable10;
	private String variable11;
	private String variable12;
	private String variable13;
	private String variable14;
	private String variable15;
	private String variable16;
	private String variable17;
	private String variable18;
	private String variable19;
	private String variable20;
	private String variable21;
	private String variable22;
	private String variable23;
	private String variable24;
	private String variable25;
	private String variable26;
	private String variable27;
	private String variable28;
	private String variable29;
	private String variable30;
	private String variable41;
	private String variable42;
	private String variable43;
	private String variable44;
	private String variable45;
	private String variable46;
	private String variable47;
	private String variable48;
	private String variable49;
	private String variable50;
}
