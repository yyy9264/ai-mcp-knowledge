# PowerShell版本的API调用脚本
$headers = @{
    "Content-Type" = "application/json"
}

$body = @{
    "model" = "deepseek-r1:1.5b"
    "prompt" = "1+1"
    "stream" = $false
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "http://f3fea4e35e67457bb199-deepseek-r1-llm-api.gcs-xy1a.jdcloud.com/api/generate" -Method Post -Headers $headers -Body $body
    Write-Host "Response Status: $($response.StatusCode)"
    Write-Host "Response Content: $($response.Content)"
} catch {
    Write-Host "Error occurred: $($_.Exception.Message)"
}