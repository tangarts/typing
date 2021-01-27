(ns typing.components.text)

(def meditations-2-1
  "BEGIN the morning by saying to thyself, I shall meet with the busybody, the ungrateful, arrogant, deceitful, envious, unsocial. All these things happen to them by reason of their ignorance of what is good and evil. But I who have seen the nature of the good that it is beautiful and of the bad that it is ugly, and the nature of him who does wrong, that it is akin to me, not [only] of the same blood or seed, but that it participates in [the same] intelligence and [the same] portion of the divinity, I can neither be injured by any of them, for no one can fix on me what is ugly, nor can I be angry with my kinsman, nor hate him. For we are made for co-operation, like feet, like hands, like eyelids, like the rows of the upper and lower teeth. To act against one another then is contrary to nature; and it is acting against one another to be vexed and to turn away.")

(def ex-1 "No matter what you're doing, the most essential thing is to not give up. Fail as many times as it takes. Keep trying persistently until you can call yourself average. If you can collect a nice group of average-level skills, that's already above-average. You've created your own sort of talent.")

(def voltaire "The library is one of the noblest of institutions. There has never been an expense more magnificent and more useful")

(def yeats "He who hath made the night of stars / For souls who tire and bleed, / Sent one of his great angels down / To help me in my need.")

(def road-not-taken-1 "TWO roads diverged in a yellow wood, And sorry I could not travel both And be one traveler, long I stood And looked down one as far as I could To where it bent in the undergrowth;")

(def e-to-a "IN these deep solitudes and awful cells,")

(def seneca "Luck is where opportunity meets preparation.")


(defn random-text []
  (rand-nth [road-not-taken-1 ex-1 voltaire yeats e-to-a seneca]))
