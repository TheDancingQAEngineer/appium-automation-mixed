# appium-automation-mixed
 
## Урок 7, Ex11, попытка 1.

Только Java.

Для проверки заголовка на iOS:
- в методы waitForArticleTitle() и getArticleTitle() передается строка с ожидаемым заголовком (на Android эта строка просто игнорируется, и используется локатор заголовка по id);
- ожидается появление WebView по локатору "xpath://XCUIElementTypeWebView";
- ищется элемент по шаблону локатора "xpath://*[@name=\"{TITLE}\"]";

