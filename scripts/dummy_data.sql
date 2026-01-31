INSERT INTO member (email, password, member_role, nickname, birth, gender, login_type, status, mbti, profile_image_url, created_date, updated_date)
VALUES (
           'talkpick@example.com',
           '$2a$10$dXJ3SW6G7P50eS.0Qj2xpO4jXnFX8DvfRw7cLb2nqDhVLqKlbKpVy',
           'MEMBER',
           'talkpick',
           '2000-01-01',
           'FEMALE',
           'EMAIL',
           'ACTIVE',
           'INFP',
           'https://dummyimage.com/100x100/ccc/fff&text=Talkpick',
           NOW(),
           NOW()
       );

INSERT INTO category (title, image_url, category_group) VALUES
                                                            ('소개팅/과팅', 'https://dummyimage.com/600x400/000/fff&text=소개팅', 'STRANGER'),
                                                            ('그룹 첫 모임', 'https://dummyimage.com/600x400/111/fff&text=그룹모임', 'STRANGER'),
                                                            ('룸메 첫 만남', 'https://dummyimage.com/600x400/222/fff&text=룸메', 'STRANGER'),
                                                            ('기타/아이스브레이킹', 'https://dummyimage.com/600x400/333/fff&text=기타', 'STRANGER'),
                                                            ('가족', 'https://dummyimage.com/600x400/444/fff&text=가족', 'CLOSE'),
                                                            ('친구', 'https://dummyimage.com/600x400/555/fff&text=친구', 'CLOSE'),
                                                            ('연인', 'https://dummyimage.com/600x400/666/fff&text=연인', 'CLOSE'),
                                                            ('동료', 'https://dummyimage.com/600x400/777/fff&text=동료', 'CLOSE');

INSERT INTO keyword (name, image_url, icon_url) VALUES
                                                    ('만약에', 'https://dummyimage.com/600x400/f44/fff&text=만약에', 'https://dummyimage.com/100x100/f44/fff&text=만약에'),
                                                    ('가치관', 'https://dummyimage.com/600x400/4f4/fff&text=가치관', 'https://dummyimage.com/100x100/4f4/fff&text=가치관'),
                                                    ('성향', 'https://dummyimage.com/600x400/44f/fff&text=성향', 'https://dummyimage.com/100x100/44f/fff&text=성향'),
                                                    ('취미', 'https://dummyimage.com/600x400/ff4/fff&text=취미', 'https://dummyimage.com/100x100/ff4/fff&text=취미'),
                                                    ('게임', 'https://dummyimage.com/600x400/f4f/fff&text=게임', 'https://dummyimage.com/100x100/f4f/fff&text=게임'),
                                                    ('여행', 'https://dummyimage.com/600x400/4ff/fff&text=여행', 'https://dummyimage.com/100x100/4ff/fff&text=여행'),
                                                    ('취향', 'https://dummyimage.com/600x400/f8f/fff&text=취향', 'https://dummyimage.com/100x100/f8f/fff&text=취향'),
                                                    ('일상', 'https://dummyimage.com/600x400/ff8/fff&text=일상', 'https://dummyimage.com/100x100/ff8/fff&text=일상'),
                                                    ('연애', 'https://dummyimage.com/600x400/f44/fff&text=연애', 'https://dummyimage.com/100x100/f44/fff&text=연애'),
                                                    ('추억', 'https://dummyimage.com/600x400/44f/fff&text=추억', 'https://dummyimage.com/100x100/44f/fff&text=추억'),
                                                    ('사회/트렌드', 'https://dummyimage.com/600x400/4ff/fff&text=트렌드', 'https://dummyimage.com/100x100/4ff/fff&text=트렌드'),
                                                    ('vs', 'https://dummyimage.com/600x400/8f4/fff&text=vs', 'https://dummyimage.com/100x100/8f4/fff&text=vs');

INSERT INTO topic (title, detail, category_id, keyword_id, status, created_date, updated_date) VALUES
                                                                                                   ('만약에 질문으로 알아가기', '만약에 상황을 통한 첫 대화 주제', 1, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('연애관 나누기', '서로의 연애관에 대해 이야기하기', 1, 9, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('vs 게임으로 취향 알아보기', 'vs 게임을 통한 대화 시작하기', 1, 12, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('취미로 공통점 찾기', '취미를 통해 서로를 알아가기', 1, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('게임으로 분위기 풀기', '게임을 통한 재미있는 아이스브레이킹', 2, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('만약에 상황 이야기', '만약에 질문으로 그룹의 분위기 풀기', 2, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('vs 질문으로 웃음 유발하기', 'vs 게임으로 그룹 활동하기', 2, 12, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('성향 알아보기', '각자의 성향을 알아보며 팀 구성하기', 2, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('여행 경험 이야기', '여행 경험을 통해 취향 알아보기', 3, 6, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('취미 활동 공유하기', '취미에 대해 이야기하며 친해지기', 3, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('생활 가치관 나누기', '룸메이트로서의 가치관 공유하기', 3, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('일상의 루틴 이야기하기', '일상 생활 패턴을 통해 이해하기', 3, 8, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('게임 대회 진행하기', '게임을 통한 아이스브레이킹', 4, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('vs 투표 게임', 'vs 게임으로 선호도 파악하기', 4, 12, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('취향 맞추기 게임', '취향에 대한 퀴즈와 게임', 4, 7, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('만약에 선택 게임', '만약에 상황 선택으로 성격 파악하기', 4, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('가족의 가치관 이야기', '가족 내 공유하는 가치관 나누기', 5, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('가족 추억 공유하기', '함께한 추억을 되살펴보기', 5, 10, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('사회 이슈에 대한 의견', '최신 트렌드나 이슈에 대해 이야기하기', 5, 11, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('일상 이야기 나누기', '요즘 어떻게 지내는지 일상 공유', 5, 8, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('만약에 가정 게임', '만약에 상황으로 친구를 더 알기', 6, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('함께할 수 있는 취미 찾기', '새로운 공통 취미 개발하기', 6, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('여행 계획 이야기', '함께 가고 싶은 여행지 이야기하기', 6, 6, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('추억 이야기하기', '좋은 추억들을 함께 회상하기', 6, 10, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('연애관 깊이 있게 나누기', '진정한 사랑과 관계에 대해 이야기하기', 7, 9, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('인생 가치관 공유하기', '앞으로 살아가면서 중요한 가치에 대해 이야기', 7, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('함께한 추억 회상하기', '지금까지의 좋은 순간들 되짚어보기', 7, 10, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('미래에 대한 성향 이야기', '앞으로의 삶의 방향에 대해 이야기하기', 7, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('직장 트렌드 이야기', '최근 업계 트렌드와 사회 이슈 논의', 8, 11, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('함께 즐길 수 있는 활동', '직장 팀으로 함께할 취미 활동 제안', 8, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('개인의 취향 알아보기', '팀원들의 취향을 이해하기', 8, 7, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('팀 게임과 활동', '게임을 통해 팀 빌딩하기', 8, 5, 'ACTIVE', NOW(), NOW());

INSERT INTO topic_stat (topic_id, e_count, i_count, s_count, n_count, f_count, t_count, j_count, p_count, like_count, teen_count, twenties_count, thirties_count, forties_count, fifties_count, male_count, female_count, select_count, average_talk_time, version)
SELECT id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
FROM topic;

INSERT INTO topic_like_history (member_id, topic_id, status, created_date, updated_date) VALUES
                                                                                             (1, 1, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 3, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 5, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 9, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 13, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 17, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 21, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 25, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 29, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 32, 'ACTIVE', NOW(), NOW());

INSERT INTO notice (admin_id, title, content, read_count, status, created_date, updated_date) VALUES
                                                                                                  (1, '공지사항 제목 1', '공지사항 내용입니다. 더미 1', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 2', '공지사항 내용입니다. 더미 2', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 3', '공지사항 내용입니다. 더미 3', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 4', '공지사항 내용입니다. 더미 4', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 5', '공지사항 내용입니다. 더미 5', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 6', '공지사항 내용입니다. 더미 6', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 7', '공지사항 내용입니다. 더미 7', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 8', '공지사항 내용입니다. 더미 8', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 9', '공지사항 내용입니다. 더미 9', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 10', '공지사항 내용입니다. 더미 10', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 11', '공지사항 내용입니다. 더미 11', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 12', '공지사항 내용입니다. 더미 12', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 13', '공지사항 내용입니다. 더미 13', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 14', '공지사항 내용입니다. 더미 14', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 15', '공지사항 내용입니다. 더미 15', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 16', '공지사항 내용입니다. 더미 16', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 17', '공지사항 내용입니다. 더미 17', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 18', '공지사항 내용입니다. 더미 18', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 19', '공지사항 내용입니다. 더미 19', 0, 'ACTIVE', NOW(), NOW()),
                                                                                                  (1, '공지사항 제목 20', '공지사항 내용입니다. 더미 20', 0, 'ACTIVE', NOW(), NOW());

INSERT INTO notice_image (notice_id, image_url, status, created_date, updated_date)
SELECT id, CONCAT('https://dummyimage.com/600x400/000/fff&text=공지이미지_', id), 'ACTIVE', NOW(), NOW()
