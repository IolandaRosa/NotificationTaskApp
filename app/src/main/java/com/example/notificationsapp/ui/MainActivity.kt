package com.example.notificationsapp.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notificationsapp.R
import com.example.notificationsapp.handlers.AlarmReceiver
import com.example.notificationsapp.handlers.NotificationHandler
import com.example.notificationsapp.model.Task
import com.example.notificationsapp.utils.dateToStringDate
import com.example.notificationsapp.utils.timeToStringTime
import com.example.notificationsapp.view_model.TaskViewModel
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity(), ListFragment.OnFragmentListTaskAddListener,
    NewTaskFragment.OnSaveTaskClickListener {

    //A atividade conhece o viewModel
    private lateinit var taskViewModel: TaskViewModel

    //Os fragmentos a iniciar
    private lateinit var listFragment: ListFragment
    private lateinit var newTaskFragment: NewTaskFragment

    //Variavel para saber se é para iniciar a lista do fragmento
    private var init = true

    //Variavel para ativar/desativar a notificaçao
    private lateinit var alarmButton: SwitchMaterial

    private lateinit var alarmManager: AlarmManager
    private lateinit var notifyPendingIntent: PendingIntent

    companion object {
        private const val TAG_LIST_FRAGMENT = "ListFragment"
        private const val TAG_NEW_TASK_FRAGMENT = "NewTaskFragment"
        private const val NOTIFICATION_INTERVAL = AlarmManager.INTERVAL_HOUR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Iniciar o ViewModel - com o singleton
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        //Iniciar o fragmento
        listFragment = ListFragment.newInstance(taskViewModel)
        newTaskFragment = NewTaskFragment.newInstance()

        //Ir buscar o botão
        alarmButton = findViewById(R.id.alarmToggle)

        //Cria o canal e inicia a notificação
        taskViewModel.createChannel(this)

        setupAlarmPendingIntent()
        setupAlarmManager()
        setupNotificationsListener()

        //Colocar o observer no metodo allTasks para que quando algo mude seja notificado
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            listFragment.resetTaskList(tasks!!)
            if (init) {
                setupFragmentList()
                init = false
            } else {
                listFragment.updateAdapter()
            }
        })
    }

    private fun setupFragmentList() {

        supportActionBar?.title = "Lista de Tarefas"

        supportFragmentManager.beginTransaction().add(
            R.id.fragment_container,
            listFragment,
            TAG_LIST_FRAGMENT
        ).commit()

    }

    private fun setupAddFragment() {
        supportActionBar?.title = "Nova Tarefa"
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, newTaskFragment,
            TAG_NEW_TASK_FRAGMENT
        ).addToBackStack(TAG_NEW_TASK_FRAGMENT).commit()
    }

    //Mudar para fragmento de nova task
    override fun onClickAddButton() {
        setupAddFragment()
        //O add to backstack serve para quando se anda para tras voltar ao outro fragmento
        //se não se colocar nada o fragmento é destruido que é que acontece no caso do da actividade inicial
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun saveTaskClick(taskName: String, flow: Boolean) {
        val task =
            Task(name = taskName, date = dateToStringDate(), hour = timeToStringTime(), flow = flow)
        taskViewModel.insert(task)

        supportFragmentManager.popBackStack()
    }

    private fun setupNotificationsListener() {
        alarmButton.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                setupAlarmPendingIntent()
                setupAlarmManager()
            } else {
                cancelAlarmManager()
            }
        }
    }

    private fun setupAlarmPendingIntent() {
        val intent = Intent(this, AlarmReceiver::class.java)

        //PendingIntent para as notificações
        notifyPendingIntent = PendingIntent.getBroadcast(
            this,
            NotificationHandler.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun setupAlarmManager() {
        val triggerTime = SystemClock.elapsedRealtime()

        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            NOTIFICATION_INTERVAL,
            notifyPendingIntent
        )
    }

    private fun cancelAlarmManager() {
        alarmManager.cancel(notifyPendingIntent)
    }
}
