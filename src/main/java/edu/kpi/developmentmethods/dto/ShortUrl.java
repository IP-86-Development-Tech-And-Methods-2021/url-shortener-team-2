package edu.kpi.developmentmethods.dto;

// DTO (data-transfer object) — объект, который используется для передачи данных
// В даном примере мы используем экспериментальную в Java15 возможность — record classes,
// чтобы сократить количество написаного кода
public record Example (String title, int rating) {}
public class ShortUrl {

    private String shortUrl;

    public ShortUrl() {
    }

    public ShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}
