package br.com.model;

import com.google.gson.annotations.SerializedName;

public record BookRecord(
        @SerializedName("volumeInfo")
        Book book
){
}