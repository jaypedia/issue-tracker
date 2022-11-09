package team20.issuetracker.service;

public interface RelatedUpdateFactory {
    RelatedUpdatable getRelatedType(UpdateType updateType);
}
