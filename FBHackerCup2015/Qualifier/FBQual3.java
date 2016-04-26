import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class FBQual3 {
	static Point goal;

	public static void main(String[] args) throws CloneNotSupportedException {
		Scanner sc = new Scanner(System.in);
		
		int cases = sc.nextInt();
		
		int count = 0;
		while (cases-- > 0) {
			int height = sc.nextInt();
			int width = sc.nextInt();
			
			char[][] maze = new char[height][width];
			
			for(int i = 0; i < height; ++i) 
				maze[i] = sc.next().toCharArray();
			
			List<Turret> turrets = new ArrayList<Turret>();
			Point me = null;
			for(int i = 0; i < maze.length; ++i) {
				for(int j = 0; j < maze[i].length; ++j) {
					char cur = maze[i][j];
					Turret t;
					switch (cur) {
					case '>':
						t = new Turret(i, j);
						t.dir = 0;
						turrets.add(t);
						break;
					case '^':
						t = new Turret(i, j);
						t.dir = 1;
						turrets.add(t);
						break;
					case '<':
						t = new Turret(i, j);
						t.dir = 2;
						turrets.add(t);
						break;
					case 'v':
						t = new Turret(i, j);
						t.dir = 3;
						turrets.add(t);
						break;
					case 'S':
						me = new Point(i, j);
						maze[i][j] = '.';
						break;
					case 'G':
						goal = new Point(i, j);
						maze[i][j] = '.';
						break;
					}
				}
			}
			State initialState = new State(turrets, me);
			int steps = findSmallestSteps(initialState, maze);
			if (steps == -1) 
				System.out.println("Case #" + (++count) + ": impossible");
			else
				System.out.println("Case #" + (++count) + ": " + steps);
		}
		
		sc.close();
	}
	
	public static int findSmallestSteps(State initialState, char[][] maze) throws CloneNotSupportedException {
		HashSet<State> visited = new HashSet<State>();
		Queue<State> q = new LinkedList<State>();
		q.offer(initialState);
		
		int[] dx = { 1, 0, -1, 0 };
		int[] dy = { 0, 1, 0, -1 };
		while (!q.isEmpty()) {
			State currentState = q.poll();

			if (visited.contains(currentState)) {
				continue;
			}

			visited.add(currentState);
			Point me = currentState.me;
			
			boolean safe = true;

			if (currentState.steps != 0)
				safe = willAvoidTurrets(currentState.me, currentState.turrets, maze);
			
			if (!safe) continue;
			
			if (me.equals(goal))
				return currentState.steps;
			
			List<Turret> updatedTurrets = new ArrayList<Turret>();
			for (Turret t: currentState.turrets) 
				updatedTurrets.add(t.clone());
			
			for (Turret t: updatedTurrets) {
				t.dir = t.dir - 1;
				if (t.dir == -1)
					t.dir = 3;
			}

			for (int i = 0; i < 4; ++i) {
				int x = me.x + dx[i];
				int y = me.y + dy[i];
				
				if (x > maze.length - 1 || x < 0 || y > maze[0].length - 1 || y < 0)
					continue;
				
				if (maze[x][y] == '.') {
					State newState = new State(updatedTurrets, new Point(x, y));
					newState.steps = currentState.steps + 1;
					q.offer(newState);
				}
			}
		}
		return -1;
	}

	private static boolean willAvoidTurrets(Point me, List<Turret> turrets, char[][] maze) {
		boolean safe = true;
		for (Turret t: turrets) {
			switch (t.dir) {
			case 0:
				if (me.x == t.x && me.y > t.y) {
					safe = false;
					for(int i = t.y + 1; i < me.y; ++i) {
						if (maze[me.x][i] != '.') {
							safe = true;
							break;
						}
					}
				}
				if (!safe) {
					//System.out.println("I was hit by turret facing RIGHT at: " + t);
					return false;
				}
				break;
			case 1: 
				if (me.y == t.y && me.x < t.x) {
					safe = false;
					for(int i = me.x + 1; i < t.x; ++i) {
						if (maze[i][me.y] != '.') {
							safe = true;
							break;
						}
					}
				}
				if (!safe) {
					//System.out.println("I was hit by turret facing UP at: " + t + " and I'm at : " + me);
					return false;
				}
				break;
			case 2:
				if (me.x == t.x && me.y < t.y) {
					safe = false;
					for (int i = me.y + 1; i < t.y; ++i) {
						if (maze[me.x][i] != '.') {
							safe = true;
							break;
						}
					}
				}
				if (!safe) {
					//System.out.println("I was hit by turret facing LEFT at: " + t);
					return false;
				}
				break;
			case 3:
				if (me.y == t.y && me.x > t.x) {
					safe = false;
					for (int i = t.x + 1; i < me.x; ++i) {
						if (maze[i][me.y] != '.') {
							//System.out.println("I'm safe because I hit: " + maze[i][me.y]);
							safe = true;
							break;
						}
					}
				}
				if (!safe) {
					//System.out.println("I was hit by turret facing DOWN at: " + t);
					return false;
				}
				break;
			}
		}
		return true;
	}
}

class State {
	List<Turret> turrets;
	Point me;
	int steps = 0;
	
	public State(List<Turret> turrets, Point me) {
		this.turrets = turrets;
		this.me = me;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((me == null) ? 0 : me.hashCode());
		result = prime * result + ((turrets == null) ? 0 : turrets.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (me == null) {
			if (other.me != null)
				return false;
		} else if (!me.equals(other.me))
			return false;
		if (turrets == null) {
			if (other.turrets != null)
				return false;
		} else if (!turrets.equals(other.turrets))
			return false;
		return true;
	}
	
	/*@Override
	public boolean equals(Object other) {
		State otherState = (State) other;
		boolean valid = me.equals(otherState.me);
		if (!valid) return false;
		Set<Turret> otherTurrets = new HashSet<Turret>(otherState.turrets);
		for (Turret t: turrets) {
			if (otherTurrets.contains(t)) return true;
		}
		return false;
	}*/
	
	
}

class Point implements Cloneable {
	int x;
	int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	@Override 
	public boolean equals(Object other) {
		Point otherPoint = (Point) other;
		return otherPoint.x == this.x && otherPoint.y == this.y;
	}*/
	
	@Override
	public Point clone() throws CloneNotSupportedException {
		return (Point) super.clone();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

class Turret extends Point implements Cloneable {
	/*
	 * 0 = right
	 * 1 = up
	 * 2 = left
	 * 3 = down
	 */
	int dir;
	
	public Turret(int x, int y) {
		super(x, y);
	}
	
	@Override
	public Turret clone() throws CloneNotSupportedException {
		return (Turret) super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + dir;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turret other = (Turret) obj;
		if (dir != other.dir)
			return false;
		return true;
	}
	
	/*
	@Override
	public boolean equals(Object other) {
		Turret otherTurret = (Turret) other;
		return this.x == otherTurret.x && this.y == otherTurret.y && this.dir == otherTurret.dir;
	}*/
	
	
}