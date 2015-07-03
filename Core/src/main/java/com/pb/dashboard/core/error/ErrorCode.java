package com.pb.dashboard.core.error;

public class ErrorCode {

    public enum SQL {
        OTHER(-1, "Error not find"),
        PERMISSION_DENIED(0, "Нет прав доступа"),
        CONNECT_NOT_CLOSED(1, "Соединение не закрыто"),
        STATEMENT_NOT_CLOSED(2, "Statement не закрыт"),
        CONNECT_NOT_CREATED(3, "Соединение не создалось"),
        STATEMENT_NOT_CREATED(4, "Statement не создан"),
        ERROR_EXECUTE(5, "Запрос не выполнен"),
        QUERY_DOES_NOT_RETURN_ANYTHING(6, "Запрос ничего не вернул"),
        INCORRECT_PARAMETERS(7, "Некорректные параметры"),
        INCORRECT_COLUMN_NAME(8, "Некорректное имя заголовка");

        private final int code;
        private final String message;

        SQL(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "SQL{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}