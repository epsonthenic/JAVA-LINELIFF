package com.iphayao.linebot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
public class MasterData {



	private @Id @GeneratedValue(strategy=GenerationType.TABLE) Long id;
	private @Version @JsonIgnore Long version;
	private String createdBy;
	private @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp createdDate;
	private String updateBy;
	private @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp updateDate;

	private String code;
	private String name;
	private String nameMessageCode;
	private String description;
	private Boolean flagActive;
	private Boolean flagOrgCode;
	private Integer numberOfColumn;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "masterdata")
    private Set<MasterDataDetail> details = new HashSet<MasterDataDetail>();

	private String variableName1;
	private String variableName2;
	private String variableName3;
	private String variableName4;
	private String variableName5;
	private String variableName6;
	private String variableName7;
	private String variableName8;
	private String variableName9;
	private String variableName10;


	private String variableType1;
	private String variableType2;
	private String variableType3;
	private String variableType4;
	private String variableType5;
	private String variableType6;
	private String variableType7;
	private String variableType8;
	private String variableType9;
	private String variableType10;


	private Boolean variableFlagRequire1;
	private Boolean variableFlagRequire2;
	private Boolean variableFlagRequire3;
	private Boolean variableFlagRequire4;
	private Boolean variableFlagRequire5;
	private Boolean variableFlagRequire6;
	private Boolean variableFlagRequire7;
	private Boolean variableFlagRequire8;
	private Boolean variableFlagRequire9;
	private Boolean variableFlagRequire10;


	private String variableDefaultValue1;
	private String variableDefaultValue2;
	private String variableDefaultValue3;
	private String variableDefaultValue4;
	private String variableDefaultValue5;
	private String variableDefaultValue6;
	private String variableDefaultValue7;
	private String variableDefaultValue8;
	private String variableDefaultValue9;
	private String variableDefaultValue10;

}
