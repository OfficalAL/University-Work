$OutDir = "./scripts/output"
New-Item -ItemType Directory -Force -Path $OutDir | Out-Null

$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession
$loginBody = @{email='alice@example.edu'; password='password123'} | ConvertTo-Json

# Helper to save response or error
function Save-Response($path, $response) {
    if ($null -eq $response) { return }
    if ($response -is [string]) {
        $response | Out-File -FilePath $path -Encoding utf8
    } else {
        $response | ConvertTo-Json -Depth 10 | Out-File -FilePath $path -Encoding utf8
    }
}

# Login
try {
    $login = Invoke-RestMethod -Uri http://127.0.0.1:5000/api/v1/users/login -Method Post -Body $loginBody -ContentType 'application/json' -WebSession $session -ErrorAction Stop
    Save-Response "$OutDir/login.json" $login
} catch {
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $reader.ReadToEnd() | Out-File "$OutDir/login_error.html" -Encoding utf8
    } else {
        $_ | Out-File "$OutDir/login_error.txt" -Encoding utf8
    }
}

# Protected: user societies
try {
    $userSoc = Invoke-RestMethod -Uri http://127.0.0.1:5000/api/v1/users/stu-0001/societies -Method Get -WebSession $session -ErrorAction Stop
    Save-Response "$OutDir/user_societies.json" $userSoc
} catch {
    if ($_.Exception.Response) { (New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())).ReadToEnd() | Out-File "$OutDir/user_societies_error.html" -Encoding utf8 } else { $_ | Out-File "$OutDir/user_societies_error.txt" -Encoding utf8 }
}

# Protected: recommendations
try {
    $recs = Invoke-RestMethod -Uri http://127.0.0.1:5000/api/v1/users/stu-0001/societies/recommendations -Method Get -WebSession $session -ErrorAction Stop
    Save-Response "$OutDir/recommendations.json" $recs
} catch {
    if ($_.Exception.Response) { (New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())).ReadToEnd() | Out-File "$OutDir/recommendations_error.html" -Encoding utf8 } else { $_ | Out-File "$OutDir/recommendations_error.txt" -Encoding utf8 }
}

# Public: all societies
try {
    $all = Invoke-RestMethod -Uri http://127.0.0.1:5000/api/v1/societies -Method Get -WebSession $session -ErrorAction Stop
    Save-Response "$OutDir/all_societies.json" $all
} catch {
    if ($_.Exception.Response) { (New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())).ReadToEnd() | Out-File "$OutDir/all_societies_error.html" -Encoding utf8 } else { $_ | Out-File "$OutDir/all_societies_error.txt" -Encoding utf8 }
}

Write-Host "Saved outputs to $OutDir"
