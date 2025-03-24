package pl.miloszgilga.ahms.mail;

import lombok.Builder;

import java.io.File;

@Builder
public record ResourceDto(String mimeVariableName, File fileHandler) {
}
