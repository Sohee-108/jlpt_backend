package team.jlpt.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class Problem {
    private String sentence;
    private String answer;
    private Map<String, String> ruby = new HashMap<>();
}
