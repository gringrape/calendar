import Vue from 'vue'
import Router from 'vue-router'
import MonthlyView from '@/components/MonthlyView'
import DailyView from '@/components/DailyView'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      components:{
        monthlyView: MonthlyView,
        dailyView: DailyView
      }, 
      props: true
    }
  ]
})
