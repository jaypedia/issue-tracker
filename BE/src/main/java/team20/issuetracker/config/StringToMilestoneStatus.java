package team20.issuetracker.config;

import org.springframework.core.convert.converter.Converter;

import team20.issuetracker.domain.milestone.MilestoneStatus;

public class StringToMilestoneStatus implements Converter<String, MilestoneStatus> {

    @Override
    public MilestoneStatus convert(String status) {
        try {
            return MilestoneStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
