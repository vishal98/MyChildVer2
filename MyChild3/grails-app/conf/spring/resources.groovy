// Place your Spring DSL code here
beans = {
}


/*// Place your Spring DSL code here
import ghumover2.SayHelloTask
beans = {
  sayHelloTask(SayHelloTask){
  }

  xmlns task: "http://www.springframework.org/schema/task"

  task.'scheduled-tasks'{
	task.scheduled(ref:'retryEmailTask', method: 'executeTask', cron: '0-59 * * * * *')
  }
}*/