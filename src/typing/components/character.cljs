(ns typing.components.character)

(def color {:white "#fff" :red "#ff0033" :black "000"})

(defn character
  "if variant red else white
  "
  [^string children variant]
  [:span {:style ((keyword variant) color)} children])
