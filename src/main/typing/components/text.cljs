(ns typing.components.text)

(def meditations-2-1
  "BEGIN the morning by saying to thyself, I shall meet with the busybody, the ungrateful, arrogant, deceitful, envious, unsocial. All these things happen to them by reason of their ignorance of what is good and evil. But I who have seen the nature of the good that it is beautiful and of the bad that it is ugly, and the nature of him who does wrong, that it is akin to me, not [only] of the same blood or seed, but that it participates in [the same] intelligence and [the same] portion of the divinity, I can neither be injured by any of them, for no one can fix on me what is ugly, nor can I be angry with my kinsman, nor hate him. For we are made for co-operation, like feet, like hands, like eyelids, like the rows of the upper and lower teeth. To act against one another then is contrary to nature; and it is acting against one another to be vexed and to turn away.")

(def ex-1 "No matter what you're doing, the most essential thing is to not give up. Fail as many times as it takes. Keep trying persistently until you can call yourself average. If you can collect a nice group of average-level skills, that's already above-average. You've created your own sort of talent.")

(def voltaire "The library is one of the noblest of institutions. There has never been an expense more magnificent and more useful")

(def seneca "Luck is where opportunity meets preparation.")

(def dune "I must not fear. Fear is the mind-killer. Fear is the little-death that brings total obliteration. I will face my fear. I will permit it to pass over me and through me. And when it has gone past I will turn the inner eye to see its path. Where the fear has gone there will be nothing. Only I will remain." )


;; https://arcana.computer/catalogs/quotes 

;  François de La Rochefoucauld 
(def roch "Sincere enthusiasm is the only orator who always persuades. It is like an art the rules of which never fail; the simplest man with enthusiasm persuades better than the most eloquent with none.")

; https://unthinking.photography/articles/on-lacework
(def pipkin "I become grateful to wake up every day knowing how I will spend it. I'm not building a cathedral, but I think about what building a cathedral would let me do, how it would allow me to move my hands in a task and see something monumental grow very slowly, with immense care. A bricklayer understands brick in a way that is devotional. Repetition is devotional.")

; https://www.gwern.net/Traffic
(def dinesen "No, no, let us work just one more hour; let us compose just one more page. Of course we shall not get the book done, but we must keep on trying to finish a little more…When you have a great and difficult task, something perhaps almost impossible, if you only work a little at a time, every day a little, without faith and without hope, suddenly the work will finish itself.")

(def eliot "Home is where one starts from. As we grow older The world becomes stranger, the pattern more complicated Of dead and living. Not the intense moment Isolated, with no before and after, But a lifetime burning in every moment And not the lifetime of one man only But of old stones that cannot be deciphered. There is a time for the evening under starlight, A time for the evening under lamplight.")

; bird by bird
(def lamott "Writing has so much to give, so much to teach, so many surprises. That thing you had to force yourself to do-the actual act of writing-turns out to be the best part. It's like discovering that while you thought you needed the tea ceremony for the caffeine, what you really needed was the tea ceremony. The act of writing turns out to be its own reward.")

; https://medium.com/conversations-with-tyler/patrick-collison-stripe-podcast-tyler-cowen-books-3e43cfe42d10
(def cowen "So, I'll pick up a book, put it down. Someone says, 'How long did it take you to read that book?' And I'll say, 'Fifty-two years,' because I've been reading since I was three, and that's the correct answer. People don't get that. It took me 52 years to read that book. So I'm not a fast reader. I'm a very, very slow reader. You're just mis-measuring the unit if you think I read something quickly.")

(def mcarty  "He sighted the terrain before him in the periodic flare of the lightning and trudged on and in this manner he rounded a dark cape of rock off to his right and came to a halt, shivering and blowing into his clawed and palsied hands. In the distance before him a fire burned on the prairie, a solitary flame frayed
by the wind that freshened and faded and shed scattered sparks down the storm like hot scurf blown from some unreckonable forge howling in the waste. He sat and watched it. He could not judge how far it was. He lay on his stomach to skylight the terrain to see what men were there but there was no sky and no light. He lay for a long time watching but he saw nothing move. When he went on again the fire seemed to recede before him. A troop of figures passed between him and the light. Then again. Wolves perhaps. He went on.\n\nIt was a lone tree burning on the desert. A heraldic tree that the passing storm had left afire. The solitary pilgrim drawn up before it had traveled far to be here and he knelt in the hot sand and held his numbed hands out while all about in that circle attended companies of lesser auxiliaries routed forth into the inordinate day, small owls that crouched silently and stood from foot to foot and tarantulas and solpugas and vinegarroons and the vicious mygale spiders and beaded lizards with mouths black as a chowdog's, deadly to man, and the little desert basilisks that jet blood from their eyes and the small sandvipers like seemly gods, silent and the same, in Jeda, in Babylon.\n\nA constellation of ignited eyes that edged the ring
of light all bound in a precarious truce before this torch whose brightness had set back the stars in their sockets.")

(defn random-text []
  (rand-nth [lamott eliot dinesen pipkin roch dune ex-1 voltaire seneca cowen]))

