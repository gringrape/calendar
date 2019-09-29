import Vue from 'vue'
import Router from 'vue-router'
import CalendarContents from '@/components/CalendarContents'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'CalendarContents',
      component: CalendarContents,
      props: true
    }
  ]
})
