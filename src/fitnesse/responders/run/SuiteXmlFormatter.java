package fitnesse.responders.run;

import fitnesse.wiki.PageCrawler;
import fitnesse.wiki.WikiPage;
import fitnesse.FitNesseContext;

public abstract class SuiteXmlFormatter extends XmlFormatter {

  private TestSummary xmlPageCounts = new TestSummary();

  public SuiteXmlFormatter(WikiPage page, FitNesseContext context) throws Exception {
    super(context, page);
  }

  private void addFinalCounts() throws Exception {
    testResponse.finalCounts = new TestResponse.Counts();
    finalSummary.right = testResponse.finalCounts.right = xmlPageCounts.right;
    finalSummary.wrong = testResponse.finalCounts.wrong = xmlPageCounts.wrong;
    finalSummary.ignores = testResponse.finalCounts.ignores = xmlPageCounts.ignores;
    finalSummary.exceptions = testResponse.finalCounts.exceptions = xmlPageCounts.exceptions;
  }
  
  @Override
  public void allTestingComplete() throws Exception {
    addFinalCounts();
    super.allTestingComplete();
  }
  
  @Override
  public void processTestResults(WikiPage testPage, TestSummary testSummary)
      throws Exception {
    PageCrawler pageCrawler = getPage().getPageCrawler();
    String relativeName = pageCrawler.getRelativeName(getPage(), testPage);
    if ("".equals(relativeName))
      relativeName = String.format("(%s)", testPage.getName());
    processTestResults(relativeName, testSummary);

    xmlPageCounts.tallyPageCounts(testSummary);
  }
  


}
