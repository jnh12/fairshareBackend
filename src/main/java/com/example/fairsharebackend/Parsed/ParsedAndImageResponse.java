package com.example.fairsharebackend.Parsed;


import com.example.fairsharebackend.GPT.GPTResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ParsedAndImageResponse {

    private byte[] ImageData;
    private GPTResponse GPTResponse;

}
