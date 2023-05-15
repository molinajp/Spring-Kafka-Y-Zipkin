package common.commonlogger.client.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractAppender extends AppenderBase<ILoggingEvent> {
    private Layout<ILoggingEvent> layout;

    @Override
    public void start() {
        if (layout == null) {
            addError("No layout set for the appender named [" + name + "].");
            return;
        }
        super.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        layout.doLayout(eventObject);
    }
}
