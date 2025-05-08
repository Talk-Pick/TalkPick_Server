package talkPick.application.admin.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.port.in.admin.AdminCommandUseCase;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCommandService implements AdminCommandUseCase {

}
