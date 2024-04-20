Title: Cryptographic Analysis Tools

Description: This project provides a set of Java tools designed for cryptographic analysis,
particularly focusing on decrypting encoded messages using various strategies. 
The tools utilize a rotor-based cryptographic system for encoding and decoding processes.

Components:
COA.java: Implements a brute force decryption strategy by trying multiple keys on a given 
ciphertext and checking if the result is in English.
OptimizedCOA.java: An optimized version of COA.java that stops decryption after the first 
successful attempt, improving performance and efficiency.
KPA.java: Uses a known-plaintext attack approach to test potential keys against a ciphertext,
refining the search for keys that yield expected plaintext results.
Rotor96Crypto.java: Provides the underlying cryptographic functionality using a rotor-based 
mechanism, supporting both encryption and decryption processes.

Usage:
Run each Java file individually to observe the decryption process.
Ensure the Rotor96Crypto class is correctly linked as it is crucial for the encryption 
and decryption processes.

Dependencies:

Ensure all Java files are in the same directory or appropriately referenced within the
project structure. 
The main cryptographic operations depend on the Rotor96Crypto class.
