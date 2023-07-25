<script>
  export let isOpen = false
</script>

<div
  class:open={isOpen}
  class="nav-icon">
  {#each Array(4) as _}
    <span></span>
  {/each}
</div>


<style>
  .nav-icon {
    width: 60px;
    height: 45px;
    position: relative;
    transform: rotate(0deg);
    transition: .5s ease-in-out;
    cursor: pointer;
    transform: scale(0.5);
  }

  .nav-icon span {
    display: block;
    position: absolute;
    height: 9px;
    width: 100%;
    background: white;
    border-radius: 9px;
    opacity: 1;
    left: 0;
    transform: rotate(0deg);
    transition: .25s ease-in-out;
  }

  .nav-icon span:nth-child(1) {
    top: 0px;
  }

  .nav-icon span:nth-child(2),.nav-icon span:nth-child(3) {
    top: 18px;
  }

  .nav-icon span:nth-child(4) {
    top: 36px;
  }

  .nav-icon.open span:nth-child(1) {
    top: 18px;
    width: 0%;
    left: 50%;
  }

  .nav-icon.open span:nth-child(2) {
    transform: rotate(45deg);
  }

  .nav-icon.open span:nth-child(3) {
    transform: rotate(-45deg);
  }

  .nav-icon.open span:nth-child(4) {
    top: 18px;
    width: 0%;
    left: 50%;
  }
</style>