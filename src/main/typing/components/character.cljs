(ns typing.components.character
  (:require 
    [typing.components.style :refer [word-css]]))

(defn character
  [expected actual variant]
  (let [is-match (= expected actual)]
    [:span {:style word-css
            :class
            [(case variant
               "done" (if is-match "correct" "incorrect")
               "current" "cursor"
               "awaiting")]}
     expected]))


(comment 
  ; alternative to case

; (cond (= variant "done")
;                    (if is-match "correct" "incorrect")
;                    (= variant "current") "cursor"
;                    :else "awaiting") 
  )

