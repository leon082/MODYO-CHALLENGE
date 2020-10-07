package modyo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
public class Pokemon {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private Integer weight ;
    @Getter
    @Setter
    private List<String> type;
    @Getter
    @Setter
    private List<String> abilities;
    @Getter
    @Setter
    private String image;


}
