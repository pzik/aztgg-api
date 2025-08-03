package com.aztgg.api.global.util;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class NicknameGenerator {

	private static final List<String> ADJECTIVES = List.of(
		"따뜻한", "시끄러운", "조용한", "똑똑한", "우아한", "수줍은", "유쾌한", "당당한", "어두운", "밝은",
		"장난꾸러기인", "기분좋은", "수상한", "엉뚱한", "고요한", "대담한", "말없는", "섬세한", "은은한", "정직한",
		"통통 튀는", "귀여운", "지적인", "엉성한", "세련된", "성실한", "모험하는", "날렵한", "느긋한", "화려한",
		"단단한", "청명한", "반짝이는", "흐릿한", "부드러운", "단정한", "활기찬", "상냥한", "다정한", "고집센",
		"말랑한", "무뚝뚝한", "깔끔한", "겸손한", "눈부신", "신비로운", "용감한", "기운찬", "기묘한", "예리한"
	);

	private static final List<String> NOUNS = List.of(
		"토끼", "고양이", "늑대", "사자", "너구리", "다람쥐", "부엉이", "독수리", "호랑이", "여우",
		"고래", "돌고래", "해파리", "상어", "문어", "판다", "코끼리", "고슴도치", "곰", "펭귄",
		"앵무새", "사슴", "공룡", "새", "까마귀", "삵", "물소", "미어캣", "알파카", "오소리",
		"기사", "마법사", "탐험가", "작곡가", "소설가", "화가", "요리사", "발명가", "농부", "정원사",
		"나무꾼", "연금술사", "요정", "사서", "도서관쥐", "도깨비", "장군", "수호자", "조련사", "모험가"
	);
	private static final int RANDOM_SUFFIX_LENGTH = 6;
	private static final String RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private static final SecureRandom random = new SecureRandom();

	private NicknameGenerator() {
	}

	public static String generate() {
		String adjective = ADJECTIVES.get(ThreadLocalRandom.current().nextInt(ADJECTIVES.size()));
		String noun = NOUNS.get(ThreadLocalRandom.current().nextInt(NOUNS.size()));

		String randomSuffix = generateRandomString();

		return adjective + " " + noun + " " +randomSuffix;
	}

	private static String generateRandomString() {
		StringBuilder sb = new StringBuilder(NicknameGenerator.RANDOM_SUFFIX_LENGTH);
		for (int i = 0; i < NicknameGenerator.RANDOM_SUFFIX_LENGTH; i++) {
			sb.append(RANDOM_CHARS.charAt(random.nextInt(RANDOM_CHARS.length())));
		}
		return sb.toString();
	}
}
