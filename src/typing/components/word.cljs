(ns typing.components.word
  (:require
    [clojure.string :as str]
    [typing.components.character :refer [character color]]))


; OK
(def word-css
  {:font-size "26"
  :margin-bottom "4px"
  :padding "4px 5px"
  :border-radius "5"
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
        style-object {:color "#111"}
        index (range (count expected))]
      [:span {:style word-css}
       (if (not= variant "current") expected
         (map #(character %1 %2)
              expected
              (map #(get-variant expected actual %)
                   index))
       )]))


(comment 

(assoc style-object :background "#3bd376")
(word expected actual "awaiting")
(def expected "hello world")
(def actual "hello wor")

(map #(get-variant expected actual %) (range (count expected)))

(def l 
  '("white" "white" "white" "white" "white" "white" "white" "white" "white" "black" "black"))

(map #(character %1 %2)
     expected
    (map #(get-variant expected actual %) (range (count expected))) )



(range (count (seq expected)))
(count expected)

)
