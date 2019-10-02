<template>
  <div class="event-consecutive event-start event-end" 
        v-popover="{ name: 'id' }"
        :data-span="period">
    {{ title }}
    <popover :name="id" :width="200">
      <div class="content-line">
        <div class="event-consecutive-marking">
        </div>
        <div class="title">
          <h5>{{ itinerary["title"] }}</h5>
          <h5 class="reservation">{{ itinerary["startDate"] }} ~ {{ itinerary["endDate"] }}</h5>
        </div>
      </div>
      <div class="content-line">
        <i class="material-icons">notes</i>
      </div>
      <div class="title">
        <h5 class="reservation">{{ itinerary["description"] }}</h5>
      </div>
    </popover> 
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Consecutive',
  props: {
    id: String,
    title: String,
    period: Number
  },
  data() {
    return {
      itinerary: {},
      pop: {}
    }
  },
  methods: {
    getItinerary: function() {
       axios.get('http://localhost:5000/itineraries/' + this.id)
      .then(response => this.itinerary = response.data)
    }
  },
  mounted() {
    this.pop.name = this.id;
    this.getItinerary();
  }
}
</script>

<style>
div[data-popover="pop"] {
  padding: 16px;
  color: #263747; }
  div[data-popover="pop"] .content-line {
      display: flex;
      align-items: flex-start; }
    div[data-popover="pop"] .content-line .event-consecutive-marking {
        margin-top: 10px;
        width: 24px;
        height: 24px; 
        border-radius: 4px;
        background-color: #0078FF; }
    div[data-popover="pop"] .content-line .title {
        margin-left: 12px; }
      div[data-popover="pop"] .content-line .title .reservation {
          display: block; }
    div[data-popover="pop"] .content-line .material-icons {
        color: #263747; }
    div[data-popover="pop"] .content-line:nth-child(2) {
        margin-top: 16px; }
</style>
