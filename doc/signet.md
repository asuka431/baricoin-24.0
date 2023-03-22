Contents
========
This document describes how to set up your own Signet network.

Launch Signet
=============

baricoin.conf settings
----------------------
Set `baricoin.conf` in the data folder(`~/.baricoin`) referring to the example below.

    [main]
    #rpcuser=
    #rpcpassword=
    #rpcauth=user:ffb389c0.......0dd878972
    rpcallowip=127.0.0.1
    server=1
    
    [signet]
    #rpcuser=
    #rpcpassword=
    #rpcauth=user:ffb389c0.......0dd878972
    rpcallowip=127.0.0.1
    server=1
    daemon=1
    #addnode=
    #signetchallenge=512102f8........4f2a51ae

Start baricoind with signet: `./baricoind -signet`

Create a signetchallenge for your own network
---------------------------------------------
Follow the steps below to get the Baricoin legacy address and pubkey.

    ./baricoind -regtest -daemon
    ./baricoin-cli -regtest createwallet "test"
    ADDR=$(./baricoin-cli -regtest getnewaddress "" "legacy")
    echo $ADDR
    PRIVKEY=$(./baricoin-cli -regtest dumpprivkey $ADDR)
    echo $PRIVKEY
    ./baricoin-cli -regtest getaddressinfo $ADDR | grep pubkey
    # "pubkey": "THE_REAL_PUBKEY"
    ./baricoin-cli -regtest stop

Your signetch allenge is below.

    signetchallenge = 5121 + THE_REAL_PUBKEY + 51ae
                    = 512102f8........4f2a51ae

Mining environment settings
---------------------------
Set the completed signetchallenge in baricoin.conf and restart baricoind. Then import the private key above.

    ./baricoind -signet
    CLI="./baricoin-cli -signet"
    $CLI createwallet "signet"
    $CLI -signet importprivkey $PRIVKEY

Mining
======

[Get fijicoin-signet-utility.zip](https://download.baricoin.org/baricoin-signet-utility/), then unzip it to the folder where you installed baricoin.
To mine the first block in your custom chain, you can run:

    cd to_baricoind_folder
    CLI="./baricoin-cli -signet"
    MINER="python3 miner.py"
    GRIND="./baricoin-util grind"
    ADDR="W4FVbSKsCyRcMPm8pAsdSsgSAtiXAyfovj"  # Note: Use legacy address

To mining block number 1, execute the following command. 
This will mine a block with the current timestamp. 
If you want to backdate the chain, you can give a different timestamp to --set-block-time. 
A UnixTime 12 hours ago is recommended.

    $MINER --cli="$CLI" generate --grind-cmd="$GRIND" --address="$ADDR" --set-block-time=-1 --nbits=1f0fffff


Using the --ongoing parameter will then cause the signet miner to create blocks indefinitely. 
It will pick the time between blocks so that difficulty is adjusted to match the provided --nbits value. 
Setting nbits to 1f0fffff will create blocks every minute.

    $MINER --cli="$CLI" generate --grind-cmd="$GRIND" --address="$ADDR" --nbits=1f0fffff --ongoing

