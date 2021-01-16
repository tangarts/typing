(ns typing.components.character)


(defn character
  "if variant red else white "
  [children variant]
  (let [color
        (cond (= variant "white") "#fff"
              (= variant "black") "ff0033"
              :else "#111")]
    [:span {:style {:color color}} children]))
