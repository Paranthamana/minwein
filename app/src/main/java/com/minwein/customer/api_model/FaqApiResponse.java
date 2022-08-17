package com.minwein.customer.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqApiResponse {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("data")
@Expose
private List<Datum> data = null;
@SerializedName("time")
@Expose
private Integer time;
@SerializedName("message")
@Expose
private String message;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

public Integer getTime() {
return time;
}

public void setTime(Integer time) {
this.time = time;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

    public class Datum {

        @SerializedName("faq_lang_id")
        @Expose
        private Integer faq_lang_id;
        @SerializedName("faq_id")
        @Expose
        private Integer faq_id;
        @SerializedName("language_code")
        @Expose
        private String language_code;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("answer")
        @Expose
        private String answer;

        public Integer getFaq_lang_id() {
            return faq_lang_id;
        }

        public void setFaq_lang_id(Integer faq_lang_id) {
            this.faq_lang_id = faq_lang_id;
        }

        public Integer getFaq_id() {
            return faq_id;
        }

        public void setFaq_id(Integer faq_id) {
            this.faq_id = faq_id;
        }

        public String getLanguage_code() {
            return language_code;
        }

        public void setLanguage_code(String language_code) {
            this.language_code = language_code;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

    }
}