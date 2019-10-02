<template>
  <div class="event event-start event-end" :data-span="period" v-popover:pop> 
    {{ title }}
  <popover>
    <div class="content-line">
      <div class="event-marking">
      </div>
      <div class="title">
        <h5>일정 1</h5>
        <h7 class="reservation">2019년 9월 15일 – 17일</h7>
        <span class="reservation-time">⋅오후 2:00~ 3:00</span>
      </div>
    </div>
    <div class="content-line">
      <i class="material-icons">
        notes
      </i>
    </div>
    <div class="title">
      <h7 class="reservation">스케쥴 메모, 설명이 나타납니다.</h7>
    </div>
  </popover>
  </div>
</template>

<script>
export default {
  name: "Ordinary",
   props: {
    id: String,
    title: String,
    period: Number
  }
}
</script>