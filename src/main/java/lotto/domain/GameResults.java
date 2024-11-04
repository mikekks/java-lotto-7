package lotto.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lotto.domain.enums.Rank;

public class GameResults {
	private final Map<Rank, Integer> gameResultMap;

	public GameResults() {
		this.gameResultMap = new HashMap<>();
	}

	public void calculateGameResult(List<Lotto> lottos, WinningLotto winningLotto, BonusNumber bonusNumber) {
		for (Lotto lotto : lottos) {
			Optional<Rank> rank = winningLotto.calculateRank(lotto, bonusNumber);
			rank.ifPresent(value -> gameResultMap.put(rank.get(), gameResultMap.getOrDefault(rank.get(), 0) + 1));
		}
	}

	public double getRoundedProfitRate() {
		double profit = 0;
		double purchaseAmount = 0;

		for (Rank rank : Rank.values()) {
			Integer matchCount = gameResultMap.getOrDefault(rank, 0);
			profit += matchCount * rank.getPrizeMoney();
		}

		double profitRate = profit / purchaseAmount;

		return Math.round(profitRate * 100) / 100.0;
	}

	public Map<Rank, Integer> getGameResultMap() {
		return gameResultMap;
	}
}
