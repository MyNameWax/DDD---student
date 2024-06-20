package cn.rzpt.user.infrastructure.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user")
@Builder
public class EsUserEntity {


    @Id
    @Field(value = "id",type = FieldType.Long)
    private Long id;

    @Field(value = "username",type = FieldType.Keyword)
    private String username;

    @Field(value = "nickname",type = FieldType.Text)
    private String nickname;

    @Field(value = "email",type = FieldType.Text)
    private String email;

}
