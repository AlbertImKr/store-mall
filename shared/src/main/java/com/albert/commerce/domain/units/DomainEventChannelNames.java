package com.albert.commerce.domain.units;

public class DomainEventChannelNames {

    public static final String DOMAIN_EVENT_CHANNEL = "domainEventChannel";
    public static final String ERROR_CHANNEL = "errorChannel";
    public static final String COMMENT_DELETED_EVENT = "CommentDeletedEvent";
    public static final String COMMENT_POSTED_EVENT = "CommentPostedEvent";
    public static final String COMMENT_UPDATED_EVENT = "CommentUpdatedEvent";
    public static final String ORDER_CANCELED_EVENT = "OrderCanceledEvent";
    public static final String ORDER_PLACED_EVENT = "OrderPlacedEvent";
    public static final String PRODUCT_UPDATED_EVENT = "ProductUpdatedEvent";
    public static final String PRODUCT_CREATED_EVENT = "ProductCreatedEvent";
    public static final String STORE_REGISTERED_EVENT = "StoreRegisteredEvent";
    public static final String STORE_UPLOADED_EVENT = "StoreUploadedEvent";
    public static final String USER_REGISTERED_EVENT = "UserRegisteredEvent";
    public static final String USER_UPDATED_EVENT = "UserUpdatedEvent";


    private DomainEventChannelNames() {
        throw new IllegalStateException("Utility class");
    }
}
