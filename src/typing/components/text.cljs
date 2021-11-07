(ns typing.components.text)

(def data
  [{:id 1
    :author "Marcus Aurelius"
    :text "BEGIN the morning by saying to thyself, I shall meet with the busybody, the ungrateful, arrogant, deceitful, envious, unsocial. All these things happen to them by reason of their ignorance of what is good and evil. But I who have seen the nature of the good that it is beautiful and of the bad that it is ugly, and the nature of him who does wrong, that it is akin to me, not [only] of the same blood or seed, but that it participates in [the same] intelligence and [the same] portion of the divinity, I can neither be injured by any of them, for no one can fix on me what is ugly, nor can I be angry with my kinsman, nor hate him. For we are made for co-operation, like feet, like hands, like eyelids, like the rows of the upper and lower teeth. To act against one another then is contrary to nature; and it is acting against one another to be vexed and to turn away."}
   {:id 2
    :author "Anon."
    :text "No matter what you're doing, the most essential thing is to not give up. Fail as many times as it takes. Keep trying persistently until you can call yourself average. If you can collect a nice group of average-level skills, that's already above-average. You've created your own sort of talent."}
   {:id 3
    :author "Voltaire"
    :text "The library is one of the noblest of institutions. There has never been an expense more magnificent and more useful"}
   {:id 4
    :author "Seneca"
    :text "Luck is where opportunity meets preparation."}
   {:id 5
    :author "Frank Herbert"
    :text "I must not fear. Fear is the mind-killer. Fear is the little-death that brings total obliteration. I will face my fear. I will permit it to pass over me and through me. And when it has gone past I will turn the inner eye to see its path. Where the fear has gone there will be nothing. Only I will remain."}
   {:id 6
    :author "François de La Rochefoucauld"
    :text "Sincere enthusiasm is the only orator who always persuades. It is like an art the rules of which never fail; the simplest man with enthusiasm persuades better than the most eloquent with none."}
   {:id 7
    :author "Pipkin"
    :text "I become grateful to wake up every day knowing how I will spend it. I'm not building a cathedral, but I think about what building a cathedral would let me do, how it would allow me to move my hands in a task and see something monumental grow very slowly, with immense care. A bricklayer understands brick in a way that is devotional. Repetition is devotional."
    :link "https://unthinking.photography/articles/on-lacework" }
   {:id 8
    :author "Anne Lamott"
    :text "Writing has so much to give, so much to teach, so many surprises. That thing you had to force yourself to do-the actual act of writing-turns out to be the best part. It's like discovering that while you thought you needed the tea ceremony for the caffeine, what you really needed was the tea ceremony. The act of writing turns out to be its own reward."
    :tag "bird by bird"}
   {:id 9
    :author "Dinesen"
    :text "No, no, let us work just one more hour; let us compose just one more page. Of course we shall not get the book done, but we must keep on trying to finish a little more...When you have a great and difficult task, something perhaps almost impossible, if you only work a little at a time, every day a little, without faith and without hope, suddenly the work will finish itself."
    :link "https://www.gwern.net/Traffic"}
   {:id 10
    :author "T.S. Eliot"
    :text "Home is where one starts from. As we grow older The world becomes stranger, the pattern more complicated Of dead and living. Not the intense moment Isolated, with no before and after, But a lifetime burning in every moment And not the lifetime of one man only But of old stones that cannot be deciphered. There is a time for the evening under starlight, A time for the evening under lamplight."}
   {:id 11
    :author "Tyler Cowen"
    :text "So, I'll pick up a book, put it down. Someone says, 'How long did it take you to read that book?' And I'll say, 'Fifty-two years,' because I've been reading since I was three, and that's the correct answer. People don't get that. It took me 52 years to read that book. So I'm not a fast reader. I'm a very, very slow reader. You're just mis-measuring the unit if you think I read something quickly."
    :link "https://medium.com/conversations-with-tyler/patrick-collison-stripe-podcast-tyler-cowen-books-3e43cfe42d10"}
   {:id 12
    :author "Amos Tversky"
    :text "The secret to doing good research is always to be a little underemployed. You waste years by not being able to waste hours."}
   {:id 13
    :author "Mads Mikkelsen"
    :text "My approach to what I do in my job — and it might even be the approach to my life — is that everything I do is the most important thing I do. Whether it’s a play or the next film. It is the most important thing. I know it’s not going to be the most important thing, and it might not be close to being the best, but I have to make it the most important thing. That means I will be ambitious with my job and not with my career. That’s a very big difference, because if I’m ambitious with my career, everything I do now is just stepping-stones leading to something — a goal I might never reach, and so everything will be disappointing. But if I make everything important, then eventually it will become a career. Big or small, we don’t know. But at least everything was important."
    :link "https://www.vulture.com/article/mads-mikkelsen-in-conversation.html"}
   
 ])

(defn random-text [] 
  (:text (rand-nth data)))

(comment
  ; TODO:
  ; 1. extract more than text 
  ; 2. Add more examples
  ; 3.
{:id ""
    :author ""
    :text ""}
)
