(ns typing.components.style)

(comment 
; :font-family "Special Elite"  
  )

(def word-css
  {:font-size 18
   :font-family "Source Code Pro, monospace"
   :overflow "hidden"
   })

(def root
  {:font-family "sans-serif"
   :width "100%"
   :height "100%"
   :min-height "100vh"
   :background "#eaf0f7"
   :display "flex"
   :flex-direction "column"
   :justify-content "center"
   :align-items "center"
   })


(def container
  {:margin "8px auto"
   :max-width 750
   :width "90%"
   })

(def board 
  {:margin-bottom "15px"
   :overflow "hidden"
   :background-color "#fff"
   :border-radius 6
   :box-shadow "2px 4px 2px #808080"
   :height "240px"
   })

(def statistics
  {:padding "12px 16px"
   :display "flex"
   :flex-direction "row"
   :background "#444"
   :color "#fff"
   :align-items "center"
   :font-size 12
   :box-shadow "1px 2px 1px #808080"
   })

(def inputs
  {:user-select "none"
   :overflow "hidden"
   })

(def button
  {:background "transparent" ;
   :border "none";
   :color "inherit"
   })
