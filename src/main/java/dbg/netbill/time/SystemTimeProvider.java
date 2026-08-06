package dbg.netbill.time;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SystemTimeProvider implements TimeProvider {

    private final Clock clock;

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

}
