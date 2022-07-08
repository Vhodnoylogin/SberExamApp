package com.exam.restservice.client.requests;

import org.springframework.core.ParameterizedTypeReference;

// Костыльный класс для получения ответа.
// Все равно я не могу получать ничего, кроме Стринга,
// так как стандартный подкапотный JSON-парсер не могёт в сложные типы и крашится.
public class CostbILRequestSender extends RequestSender<String> {

    public static CostbILRequestSenderBuilderFinal builderCostbILRequestSender() {
        return new CostbILRequestSenderBuilderFinal();
    }


    public abstract static class CostbILRequestSenderBuilder<C extends CostbILRequestSender, B extends CostbILRequestSenderBuilder<C, B>>
            extends RequestSenderBuilder<String, C, B> {
        {
            this.type = new ParameterizedTypeReference<>() {
            };
        }

        @Override
        public B setType(ParameterizedTypeReference<String> type) {
            return _this();
        }
    }

    public static class CostbILRequestSenderBuilderFinal extends CostbILRequestSenderBuilder<CostbILRequestSender, CostbILRequestSenderBuilderFinal> {
        @Override
        protected CostbILRequestSender _new() {
            return new CostbILRequestSender();
        }

        @Override
        public CostbILRequestSenderBuilderFinal _this() {
            return this;
        }
    }
}
