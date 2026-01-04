package io.github.luyang.starter.security.support.identity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * 统一身份接口
 *
 * @author yang.lu
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	// JSON 中通过 type 字段区分 (USER/CLIENT)
	property = "type",
	visible = true
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = UserIdentity.class, name = "USER"),
	@JsonSubTypes.Type(value = ClientIdentity.class, name = "CLIENT")
})
public sealed interface Identity extends Serializable permits UserIdentity, ClientIdentity {

	String getType();
}
