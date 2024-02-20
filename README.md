# Что это?
Приложение для заказа еды! Домашнее задание по курсу КПО, Судакова Дарья из группы БПИ227.
## Как использовать?
### Регистрация/вход
Для того чтобы начать использовать приложение, необходимо зарегистрироваться или войти! Для этого выберите опцию sign in или sign up с помощью печати нужной цифры, а затем введите свой логин или пароль. При регистрации необходимо указать, нужны ли вам права пользователя-администратора. При попытке зарегистрировать уже имеющийся в системе аккаунт, приложение попросит вас войти через опцию sign in.
### Опции для администратора
Администратор может просматривать и редактировать меню, добавлять новые блюда, а также смотреть текущую выручку. Если вы зашли под логином администратора, выберите нужную опцию с помощью нажатия нужной цифры. Для добавления нового блюда введите его название, цену, количество готовых блюд в настоящее время и время приготовления. Для редактирования выберите нужное блюдо из меню, выберите, какой параметр вы хотите изменить, и введите новое значение.
Для того чтобы выйти из аккаунта, выберите опцию log out. Для того чтобы завершить программу, выберите опцию exit.
### Опции для посетителя
# Реализация
## Хранение данных
Все данные хранятся в json файлах в папке [jsonDB](src/main/resources/jsonDB). Данные считываются, изменяются и сохраняются сериализацией с помощью библиотеки jackson.
## Паттерны
### Фасад
В регистрации используется паттерн фасад, где класс [AuthorizatorDB](src/main/kotlin/restaurant/auth/AuthorizatorDB.kt) является классом-фасадом.
Такой же паттерн используется для обработки UI, в классе [ConsoleOptionChooser](src/main/kotlin/restaurant/ui/ConsoleOptionChooser.kt).
### Стратегия 
Используется в классах [UserStrategy](src/main/kotlin/restaurant/ui/UserStrategy.kt), [AdminUserStrategy](src/main/kotlin/restaurant/ui/AdminUserStrategy.kt) и [VisitorUserStrategy](src/main/kotlin/restaurant/ui/VisitorUserStrategy.kt) для вывода меню в зависимости от статуса пользователя - администратор или посетитель. Для определения стратегии используется контекст [UserContext](src/main/kotlin/restaurant/ui/UserContext.kt).
