# bookmarkerhelper
auto create book marker for pdf file
This is a project to create book markers for pdf book automaticly.
howerver this just a framework，and you can freely extend this project by extend the interface Hanler
how can use it：
like this
public class Main {
    public static void main(String[] args) throws Exception {
        BookmarkerContext.registerHandler(new GeneralHandler());
        new BookmarkerCreator().createBookmarker("xxxx.pdf");
    }
}

There is a GeneralHandler class which I create for default use. I make use of line 
pattern and line itself information to extract title.  Hornestly this implement is not work well.
However this just is the first version, I think that I can find an affective method in the futrue
