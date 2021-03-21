(ns typing.components.dialog
  (:require
    [typing.components.percentile :as percentile]
    ))


(defn results-dialog []
  [:results-dialog
   {:open "typing-state is finished?"
    :expected "words todo"
    :actual "input-words todo"
    :close-dialog "close-dialog"}])

(def percentageBar {
  :min-width 360
  :width "100%"
  :height "22"
  :background "#ddd"
  :position "relative"
  :overflow "hidden"
  :borderRadius "20"
  })

(defn complete-percentage-bar
  [percentage is-error?]
 {:width (str percentage "%")
  :height "100%"
  :background (if is-error? "#fd3c01" "#009688")
  :borderRadius 20
  :position "absolute" })


