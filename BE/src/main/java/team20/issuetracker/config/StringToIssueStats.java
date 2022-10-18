package team20.issuetracker.config;

import org.springframework.core.convert.converter.Converter;

import team20.issuetracker.domain.issue.IssueStatus;

public class StringToIssueStats implements Converter<String, IssueStatus> {

    @Override
    public IssueStatus convert(String status) {
        try {
            return IssueStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
