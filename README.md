# Pharos DApp Support Agent[cite: 12]

An intelligent support agent developed in **Java** for the Pharos Agent Arena that takes a failed transaction hash, fetches its receipt via Chain MCP, identifies the exact failure cause, and explains why it failed in plain language with actionable fixes[cite: 12].

## 📋 Requirements Met[cite: 12]

1. **Chain MCP Integration (Req. 01):** Accepts a transaction hash and fetches the transaction and receipt via Chain MCP[cite: 12].
2. **Failure Cause Identification (Req. 02):** Identifies specific failure causes including out of gas, insufficient balance, custom reverts, and allowance errors[cite: 12].
3. **Plain Language & Fixes (Req. 03):** Explains errors without technical jargon and suggests a clear fix for the user[cite: 12].
4. **Multiple Failure Types (Req. 04):** Demonstrated and tested across at least 3 different failure types[cite: 12].

## 🚀 How to Run

1. Make sure you have **Java JDK** installed.
2. Compile the file:
   ```bash
   javac DappSupportAgent.java
