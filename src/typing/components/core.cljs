(ns typing.components.core
  (:require [typing.components.style :as style]
;            [typing.components.word :refer [word]]
            [clojure.string :as str]))


(defn randint
  [start end]
  (+ start (int (* end (rand)))))

(def vocab (str/split "about all also and because but by can come could
                      day even find first for from get give go have he her
                      here him his how if in into it its just know like look
                      make man many me more my new no not now of on one only
                      or other our out people say see she so some take tell
                      than that the their them then there these they thing
                      think this those time to two up use very want way we well
                      what when which who will with would year you your" #" "))
 

(defn generate-words
  [n]
  (into []
        (for [x (range n)]
          (nth vocab (randint 0 (- (count vocab) 1))))))


(defn get-word-type 
  [a b]
  (cond 
    (< a b) "done"
    (= a b) "current"
    :else "awaiting"))

(def typing-state
  #{"not-started"
    "in-prog"
    "awaiting"
    "finished"})

(def cpm (atom 0))

(def state (atom
             {:input-words []
              :i 0
              :time 12
              :typing-state
              (typing-state "not-started")}))

; characters is wrong, doesnt include spaces
; characters should handle text better
(defn get-cpm
  [input-words]
  (let [time-elapsed (@state :time)
        characters (reduce + (map count input-words))]
    (/ (Math/floor (* characters 60)) time-elapsed)))



(comment 
  (get-cpm input-words)
  (def input-words (generate-words 10))

  ;initialize state
  (def xy-atom (atom {:x 0 :y 0}))

  (defn inc-x! [i]
    (swap! xy-atom update-in [:x] + i))
  (defn inc-y! [i]
    (swap! xy-atom update-in [:y] + i))

  ; or use a method to update any of the keys
  (defn inc-any! [key i]
    (swap! xy-atom update-in [key] + i))

  (map count (generate-words 10))
  (inc-any! :x 3)

  (swap! state update-in [:time] inc)
)



;; ???
(defn run-timer []
  (defn set-timeout []
    (let [time- (@state :time)]
      (if (< time- 60) (do
                         (swap! state update-in [:time] inc)
                         (run-timer))
        (end-timer)))) 100)

(defn end-timer []
  (swap! 
    state
    assoc :typing-state (typing-state "awaiting")))

(print @state)


[:div {:style style/root}
 [:div {:style style/container}
  [:paper {:elevation "4"
           :style style/board}
   [:paper {:elevation "2"}
    [:div {:style style/statistics}
     [:div "timer" ]
     [:div {:style
            {:margin-left "20"}} "CPM: TODO "]
     [:div {:style
            {:flex-grow "1"}}]
     [:icon-button {:color "inherit" 
                    :onclick "reset"}
      [:i {:class "material-icons"} "replay" ]]]]
   [:div {:style {:padding "35px 15px"}}
    [:div {:ref "word-ref"
           :style style/inputs}
     ]]]
  [:paper {:style {:padding "0px"}}
   [:text-field 
    {:input-ref "input-ref TODO"
     :placeholder "start typing here"
     :margin "none"
     :full-width "true"
     :variant "outlined"
     :value "Current TODO"
     :onkeydown "fn on-key-down"}]]]]

(defn results-dialog []
  [:results-dialog
   {:open "typing-state is finished?"
    :expected "words todo"
    :actual "input-words todo"
    :close-dialog "close-dialog"}])

