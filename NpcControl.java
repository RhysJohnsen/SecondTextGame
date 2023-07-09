public class NpcControl implements Runnable{
	
	Player player;
	
	public NpcControl(Player playerIn){
		player = playerIn;
	}
	
	public Thread runNPC()throws InterruptedException{
		NpcControl r1 = new NpcControl(player);
		Thread t1 = new Thread(r1);
		t1.start();
		return t1;
	}
	
	
	public void run(){
		Thread.currentThread().suspend();
		while(true){
			int beings = player.curTile.beingCount;
			for(int i = 0; i < beings; i++){
				if(player.curTile.beings[i].health > 0 && player.curTile.beings[i].dmgpts > 0 && player.curTile.beings[i].aggressive){
					player.changeHealth(-player.curTile.beings[i].dmgpts, player.curTile.beings[i].name);
				}
			}
			Thread.currentThread().suspend();
		}
	}
}