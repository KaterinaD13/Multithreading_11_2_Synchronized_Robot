# Задача 1. Робот-доставщик
## Описание
Представьте, что перед вашей командой поставили задачу разработать программное обеспечение для робота-доставщика. Инструкции для робота содержат команды:

R — поверни направо;  
L — поверни налево;  
F — двигайся вперёд.  
В процессе построения карты маршрутов вам поручили проанализировать разнообразие существующих путей. Для генерации маршрутов вы используете функцию:  

```java
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
```
Напишите многопоточную программу, которая в каждом потоке:  

* генерирует текст generateRoute("RLRFR", 100);
* считает количество команд поворота направо (буквы 'R');
* выводит на экран результат.
  
Количество потоков равно количеству генерируемых маршрутов и равно 1000.

Так как буква 'R' в параметре генератора упомянута три раза из пяти, то в среднем, количество букв 'R' должно составлять 60% от всех команд инструкции, т. е. около 60-ти от общего числа маршрутов.

Но т. к. процесс вероятностный, точное количество раз, которое встретится эта буква в каждом маршруте, может немного отклоняться от среднего значения.

Запустив программу, вы обратили внимание, что размеры промежутков с буквой 'R' часто повторяются, и вам стало интересно, какие размеры попадаются чаще всего.

Для этого вы завели мапу в статическом поле public static final Map<Integer, Integer> sizeToFreq, которая после завершения потоков должна хранить в ключах попавшиеся частоты буквы 'R', а в значениях — количество раз их появления.

После обработки каждой строки вам нужно будет обновить эту мапу, увеличив значение для полученной частоты в ней на 1, а если частоты в мапе не было — только вставить значение 1.

Возникает проблема — Map не потокобезопасна, к ней нельзя обращаться из разных потоков одновременно.

Решением этой проблемы будет использование блока синхронизации (synchronized).

Реализуйте этот функционал и в конце основного потока выведите сообщение вида:

```java
Самое частое количество повторений 61 (встретилось 9 раз)
Другие размеры:
- 60 (5 раз)
- 64 (3 раз)
- 62 (6 раз)
...
```
## Реализация
1. Заведите мапу в статическом поле public static final Map<Integer, Integer> sizeToFreq.
2. При обработке каждой строки увеличьте счётчик в Map.
3. Используйте synchronized для потокобезопасного доступа к Map.
   
Отправьте на проверку ссылку на репозиторий с решением.