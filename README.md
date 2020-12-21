I tried to polish this app as much as I could while fulfilling the requirements within the timeframe allotted.

1. On the home screen, it shows the masthead "New York Times" in the font closest to that used by the NYT.
2. Below it is the current date.
3. At the bottom is a list of article previews from NYT home page, each presented with a section, a headline, an abstract, and authors, each rendered in a different font, size, and style.
4. Between the current date and the list of article previews is a search button, with the following functions:  
   a. You can enter a query string, then press Enter or the Go button to perform the query.  
   b. You can press the button on the left with the query symbol to make the query textbox and the Go button disappear, or press the button again to restore them.  
   c. If the query string is empty after the Enter or the Go button is pressed, then the headline article previews appear.
5. Once a search is entered, then a list of 10 article previews is returned.  
   a. Each preview is similar to that described above, with the addition of a thumbnail picture if any, positioned to the right of the title.  
   b. Each query fetches 10 articles. If you scroll down and reach the end of the query result, another 10 articles will be fetched.
6. MVVM is employed so that the list is retained even when the views are destroyed, as when the device is rotated.
7. Upon clicking any article on either the headlined articles or the articles from a query, a detail view appears.  
   a. To preserve the integrity of the original NYT article, including font size, arrangements, links, pictures, a WebView was employed to display the article in its details via its URL.  
   b. In the detail view, there is a menu item thru which you can email a link of the article.
8. Aside from MVVM, other Android best practices are employed, such as dependency injection with Dagger, Room database, etc.
