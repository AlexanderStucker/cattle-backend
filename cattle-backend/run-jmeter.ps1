$resultsFile = "reports/jmeter/results.jtl"
$reportDir = "src/test/java/com/example/reports/jmeter"

# Remove old results.jtl
if (Test-Path $resultsFile) {
    Remove-Item $resultsFile -Force
}

# Remove old HTML report folder
if (Test-Path $reportDir) {
    Remove-Item $reportDir -Recurse -Force
}

# Run JMeter Test Plan
jmeter -n -t src/test/java/com/example/jmeter/cattle-loadtest.jmx -l $resultsFile -e -o $reportDir

start src\test\java\com\example\reports/jmeter/index.html
