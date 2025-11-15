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
                                                    ('재미', 'https://dummyimage.com/600x400/f44/fff&text=재미', 'https://dummyimage.com/100x100/f44/fff&text=재미'),
                                                    ('로맨스', 'https://dummyimage.com/600x400/4f4/fff&text=로맨스', 'https://dummyimage.com/100x100/4f4/fff&text=로맨스'),
                                                    ('감동', 'https://dummyimage.com/600x400/44f/fff&text=감동', 'https://dummyimage.com/100x100/44f/fff&text=감동'),
                                                    ('일상', 'https://dummyimage.com/600x400/ff4/fff&text=일상', 'https://dummyimage.com/100x100/ff4/fff&text=일상'),
                                                    ('성장', 'https://dummyimage.com/600x400/f4f/fff&text=성장', 'https://dummyimage.com/100x100/f4f/fff&text=성장');

INSERT INTO topic (title, detail, category_id, keyword_id, status, created_date, updated_date) VALUES
                                                                                                   ('소개팅 대화 팁 1', '처음 만나는 이성과의 대화 주제 1', 1, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('소개팅 대화 팁 2', '처음 만나는 이성과의 대화 주제 2', 1, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('소개팅 대화 팁 3', '처음 만나는 이성과의 대화 주제 3', 1, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('소개팅 대화 팁 4', '처음 만나는 이성과의 대화 주제 4', 1, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('소개팅 대화 팁 5', '처음 만나는 이성과의 대화 주제 5', 1, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('그룹모임 아이스브레이킹 1', '처음 모인 그룹에서 어색함을 깨는 질문 1', 2, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('그룹모임 아이스브레이킹 2', '처음 모인 그룹에서 어색함을 깨는 질문 2', 2, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('그룹모임 아이스브레이킹 3', '처음 모인 그룹에서 어색함을 깨는 질문 3', 2, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('그룹모임 아이스브레이킹 4', '처음 모인 그룹에서 어색함을 깨는 질문 4', 2, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('그룹모임 아이스브레이킹 5', '처음 모인 그룹에서 어색함을 깨는 질문 5', 2, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('룸메이트 대화주제 1', '함께 살아갈 룸메이트와의 첫 대화 1', 3, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('룸메이트 대화주제 2', '함께 살아갈 룸메이트와의 첫 대화 2', 3, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('룸메이트 대화주제 3', '함께 살아갈 룸메이트와의 첫 대화 3', 3, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('룸메이트 대화주제 4', '함께 살아갈 룸메이트와의 첫 대화 4', 3, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('룸메이트 대화주제 5', '함께 살아갈 룸메이트와의 첫 대화 5', 3, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('아이스브레이킹 게임 1', '어색한 분위기를 깨는 아무말 대잔치 1', 4, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('아이스브레이킹 게임 2', '어색한 분위기를 깨는 아무말 대잔치 2', 4, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('아이스브레이킹 게임 3', '어색한 분위기를 깨는 아무말 대잔치 3', 4, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('아이스브레이킹 게임 4', '어색한 분위기를 깨는 아무말 대잔치 4', 4, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('아이스브레이킹 게임 5', '어색한 분위기를 깨는 아무말 대잔치 5', 4, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('가족 대화주제 1', '가족끼리도 서로를 더 알아가기 위한 질문 1', 5, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('가족 대화주제 2', '가족끼리도 서로를 더 알아가기 위한 질문 2', 5, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('가족 대화주제 3', '가족끼리도 서로를 더 알아가기 위한 질문 3', 5, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('가족 대화주제 4', '가족끼리도 서로를 더 알아가기 위한 질문 4', 5, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('가족 대화주제 5', '가족끼리도 서로를 더 알아가기 위한 질문 5', 5, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('친구 대화주제 1', '친구 사이에도 새로운 면을 발견할 수 있는 질문 1', 6, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('친구 대화주제 2', '친구 사이에도 새로운 면을 발견할 수 있는 질문 2', 6, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('친구 대화주제 3', '친구 사이에도 새로운 면을 발견할 수 있는 질문 3', 6, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('친구 대화주제 4', '친구 사이에도 새로운 면을 발견할 수 있는 질문 4', 6, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('친구 대화주제 5', '친구 사이에도 새로운 면을 발견할 수 있는 질문 5', 6, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('연인 대화주제 1', '사랑하는 사람과 더 깊은 이야기를 나누기 위한 질문 1', 7, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('연인 대화주제 2', '사랑하는 사람과 더 깊은 이야기를 나누기 위한 질문 2', 7, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('연인 대화주제 3', '사랑하는 사람과 더 깊은 이야기를 나누기 위한 질문 3', 7, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('연인 대화주제 4', '사랑하는 사람과 더 깊은 이야기를 나누기 위한 질문 4', 7, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('연인 대화주제 5', '사랑하는 사람과 더 깊은 이야기를 나누기 위한 질문 5', 7, 5, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('동료 대화주제 1', '직장 동료, 팀원들과 알아가며 친해지는 시간 1', 8, 1, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('동료 대화주제 2', '직장 동료, 팀원들과 알아가며 친해지는 시간 2', 8, 2, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('동료 대화주제 3', '직장 동료, 팀원들과 알아가며 친해지는 시간 3', 8, 3, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('동료 대화주제 4', '직장 동료, 팀원들과 알아가며 친해지는 시간 4', 8, 4, 'ACTIVE', NOW(), NOW()),
                                                                                                   ('동료 대화주제 5', '직장 동료, 팀원들과 알아가며 친해지는 시간 5', 8, 5, 'ACTIVE', NOW(), NOW());

INSERT INTO topic_stat (topic_id, e_count, i_count, s_count, n_count, f_count, t_count, j_count, p_count, like_count, teen_count, twenties_count, thirties_count, forties_count, fifties_count, male_count, female_count, select_count, average_talk_time, version)
SELECT id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
FROM topic;

INSERT INTO topic_like_history (member_id, topic_id, status, created_date, updated_date) VALUES
                                                                                             (1, 1, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 3, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 5, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 7, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 10, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 15, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 20, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 25, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 30, 'ACTIVE', NOW(), NOW()),
                                                                                             (1, 35, 'ACTIVE', NOW(), NOW());

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
FROM notice;