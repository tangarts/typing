(ns typing.components.word
  (:require
    [clojure.string :as str]
    [typing.components.character :refer [character color]]))


; OK
(def word-css
  {:font-size 16
  :border-radius 5
  :display "inline-block"})


; OK
(defn get-variant 
  [expected actual i]
  (cond 
    (or (nil? actual) (<= (count actual) i)) "black"
    (= (nth expected i) (nth actual i)) "white"
    :else "red"))


; OK
; FEATURES TO ADD
(defn word 
  "variant: current done awaiting"
  [expected actual variant]
  (let [is-match (= expected actual)
        index (range (count expected))
        style-obj 
        (cond (= variant "done") 
              {:color (if is-match "#1c54ff" "#f20434")}
              (= variant "current")
              {:background "#3bd376"}
              :else {:color "#111"}
              )]
    (into [:span {:style (merge word-css style-obj)}] 
       (if (not= variant "current") expected
         (map #(character %1 %2) expected
              (map #(get-variant expected actual %) index))))))


(comment 
(def expected "hello")
(def actual "hel ")
(word expected actual "current")

(def l 
  ["white" "white" "white" "white" "white" "white" "white" "white" "white" "black" "black"])

(doseq [[i word] (map-indexed list l)] (println i word))
(into [:span {:style (merge word-css {:color "#fff"})}]
(map #(character %1 %2) expected 
     (map #(get-variant expected actual %) (range (count expected))))
)

(range (count (seq expected)))
(count expected)

)
